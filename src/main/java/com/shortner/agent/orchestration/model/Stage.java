package com.shortner.agent.orchestration.model;
import java.util.*;
public enum Stage { REQUIREMENTS, DESIGN, IMPLEMENTATION, TESTING, DOCUMENTATION, RELEASE_READINESS, DEPLOYMENT;
 public Set<Stage> dependencies(){return switch(this){case REQUIREMENTS->Set.of();case DESIGN->Set.of(REQUIREMENTS);case IMPLEMENTATION->Set.of(DESIGN);case TESTING->Set.of(IMPLEMENTATION);case DOCUMENTATION->Set.of(DESIGN);case RELEASE_READINESS->Set.of(TESTING,DOCUMENTATION);case DEPLOYMENT->Set.of(RELEASE_READINESS);};} public boolean requiresApproval(){return this==DESIGN||this==DEPLOYMENT;} }
