package com.shortner.agent.orchestration.repository;
import com.shortner.agent.orchestration.entity.StageExecution; import com.shortner.agent.orchestration.model.Stage; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface StageExecutionRepository extends JpaRepository<StageExecution,Long>{List<StageExecution> findByRunId(Long runId);Optional<StageExecution> findByRunIdAndStage(Long runId,Stage stage);}
