package com.shortner.agent.orchestration.repository;
import com.shortner.agent.orchestration.entity.AuditEvent; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface AuditEventRepository extends JpaRepository<AuditEvent,Long>{List<AuditEvent> findByRunIdOrderByAtAsc(Long runId);}
