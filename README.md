# Governed Agentic URL Shortener

A runnable Spring Boot prototype that couples a URL-shortening service with a stateful SDLC orchestration system. Agents can carry work through a dependency graph; humans retain approval authority at architecture and deployment boundaries.

## Run and test

Requires Java 21. Start with `./gradlew bootRun` (Windows: `gradlew.bat bootRun`) and test with `./gradlew test`.

The in-memory H2 console is available at `/h2-console`; JDBC URL is `jdbc:h2:mem:urlshortener`.

## Core URL API

| Operation | Endpoint | Result |
| --- | --- | --- |
| Create | `POST /api/urls` | `{ "url": "https://example.com" }` → short code |
| Resolve | `GET /{code}` | 302 redirect and atomically records a click |
| Analytics | `GET /api/urls/{code}/analytics` | target, creation time, click count, active flag |
| Disable | `DELETE /api/urls/{code}` | disables future redirects |

Only absolute `http`/`https` targets are accepted. Codes use `SecureRandom`; database uniqueness is the final collision guard.

## Orchestration model

The workflow is an explicit persisted DAG, not a linear prompt chain:

```text
REQUIREMENTS → DESIGN [human approval] → IMPLEMENTATION ┬→ TESTING ─────┐
                                                        └→ DOCUMENTATION ─┼→ RELEASE_READINESS → DEPLOYMENT [human approval]
                                                                               
```

`RELEASE_READINESS` opens only after both parallel branches succeed. Every transition creates an `AuditEvent` with timestamp, stage, evidence/decision and approval identity. Stage and run state are persisted in H2, allowing an interrupted workflow to be inspected or resumed.

| Control | Behavior |
| --- | --- |
| Entry/exit gates | Dependencies must succeed before work becomes `READY`; success requires evidence. |
| Human oversight | `DESIGN` and `DEPLOYMENT` stop at `WAITING_APPROVAL`. |
| Retry/safe-stop | A failed stage retries at most 3 attempts, then safe-stops the run. |
| Change control | `replan` invalidates all downstream completed/in-flight artifacts after requirement change. |
| Rollback | An authorized rollback closes the run as `ROLLED_BACK` with its reason. |
| Policy boundaries | URL input validation, approval gates, evidence requirement, bounded retries and immutable-style audit events are enforced in the service. |

### Workflow API

Start with `POST /api/workflows`:

```json
{"scenario":"greenfield","requirement":"Create a branded URL shortener with click analytics"}
```

Then use `POST /api/workflows/{id}/stages/{stage}/execute` with `{"success":true,"evidence":"link to PR/test report"}`. For gated stages call `/approve` first with `{"approver":"engineering-manager"}`. `POST /{id}/replan` and `/rollback` accept `{"reason":"..."}`. Inspect a run with `GET /api/workflows/{id}` and reliability aggregates with `GET /api/workflows/metrics` (success rate, retry/rollback frequency and average end-to-end latency).

## Three required scenarios

1. **Greenfield — initial shortening service:** normalize target URL, uniqueness, redirects and analytics into acceptance criteria; approve data/API design; implement; run unit/integration tests and documentation in parallel; synchronize at release readiness.
2. **Brownfield — add link disablement:** impact analysis identifies `ShortUrl.active`, redirect resolution, analytics and deletion API. The workflow records migration/backward-compatibility evidence, tests that disabled codes return 404, then waits for release approval.
3. **Ambiguous — “support branded links”:** requirements stage records open questions (custom domain ownership, DNS validation, tenant boundaries, reserved codes). It should not progress to implementation until a human approves the selected design; a later answer invokes `replan`, invalidating dependent outputs.

## Design decisions, validation, and limits

The shortener separates controller, validation/service, and persistence concerns; transactional resolution ensures analytics increments occur with redirect selection. Tests cover happy-path redirect/click analytics, input rejection, dependency gating, approval, and parallel synchronization.

This is a prototype: H2 is not a production analytics store; click aggregation is synchronous; there is no authentication, rate limit, expiration, abuse scanning, distributed lock, asynchronous queue, or real LLM/CI/CD connector. Production deployment would add OIDC/RBAC, tenant/domain allow-lists, privacy retention policy, async event capture, observability export, durable migrations, idempotency keys, and a connector whose credentials/actions are restricted by the same approval policy.
