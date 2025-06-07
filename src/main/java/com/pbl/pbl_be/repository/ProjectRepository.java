package com.pbl.pbl_be.repository;

import com.pbl.pbl_be.model.Project;
import com.pbl.pbl_be.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


public interface ProjectRepository extends JpaRepository<Project, Integer> {
    List<Project> findProjectsByStatus(Project.Status status);
    List<Project> findAllByStatus(Project.Status status, Sort sort);
    Project findByProjectId(Integer projectId);
    List<Project> findProjectsByPm(Project project);

    @Query("SELECT p FROM Project p WHERE p.pm.userId = :pmId")
    List<Project> findProjectsByPmId(@Param("pmId") Integer pmId);

    @Query("SELECT p FROM Project p WHERE p.endTime < :currentDate AND p.status != :finishedStatus")
    List<Project> findExpiredProjects(@Param("currentDate") LocalDate currentDate,
                                      @Param("finishedStatus") Project.Status finishedStatus);

    // Cập nhật batch status cho các project đã hết hạn
    @Modifying
    @Query("UPDATE Project p SET p.status = :finishedStatus, p.updatedAt = :updatedAt " +
            "WHERE p.endTime < :currentDate AND p.status != :finishedStatus")
    int updateExpiredProjectsStatus(@Param("currentDate") LocalDate currentDate,
                                    @Param("finishedStatus") Project.Status finishedStatus,
                                    @Param("updatedAt") java.time.LocalDateTime updatedAt);
}
