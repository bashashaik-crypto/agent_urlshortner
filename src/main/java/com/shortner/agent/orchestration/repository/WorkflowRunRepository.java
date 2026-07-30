package com.shortner.agent.orchestration.repository;
import com.shortner.agent.orchestration.entity.WorkflowRun; import org.springframework.data.jpa.repository.JpaRepository;
public interface WorkflowRunRepository extends JpaRepository<WorkflowRun,Long>{}
