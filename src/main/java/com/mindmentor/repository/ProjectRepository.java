package com.mindmentor.repository;

import com.example.public_.tables.records.ProjectRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.example.public_.tables.Project.PROJECT;

@Repository
public class ProjectRepository {
    private final DSLContext dslContext;

    public ProjectRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public int saveProject(String videoUrl, String description, double price) {
        return Objects.requireNonNull(dslContext.insertInto(PROJECT, PROJECT.VIDEO_URL, PROJECT.DESCRIPTION, PROJECT.PRICE)
                        .values(videoUrl, description, price)
                        .returning(PROJECT.ID)
                        .fetchOne())
                .getId();
    }

    public ProjectRecord getProjectById(int projectId) {
        return dslContext.selectFrom(PROJECT)
                .where(PROJECT.ID.eq(projectId))
                .fetchOne();
    }

    public List<ProjectRecord> getAllProjects() {
        return dslContext.selectFrom(PROJECT)
                .fetch();
    }

    public int updateProject(int projectId, String videoUrl, String description, double price) {
        return dslContext.update(PROJECT)
                .set(PROJECT.VIDEO_URL, videoUrl)
                .set(PROJECT.DESCRIPTION, description)
                .set(PROJECT.PRICE, price)
                .where(PROJECT.ID.eq(projectId))
                .execute();
    }

    public int deleteProject(int projectId) {
        return dslContext.deleteFrom(PROJECT)
                .where(PROJECT.ID.eq(projectId))
                .execute();
    }
}