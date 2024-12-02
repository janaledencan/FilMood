package hr.ferit.filmood.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.Nullable;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity implements Persistable<UUID> {

    @Nullable
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;

    @Nullable
    @CreatedDate
    @Column(name = "created_at")
    protected Instant createdAt;

    @Nullable
    @LastModifiedDate
    @Column(name = "modified_at")
    protected Instant modifiedAt;

    @Version
    @Column(name = "version")
    protected Integer version;

    @Override
    @Nullable
    public UUID getId() {
        return id;
    }

    public void setId(@Nullable UUID id) {
        this.id = id;
    }

    @Override
    public boolean isNew() {
        return Objects.isNull(this.id);
    }

    @Nullable
    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(@Nullable Instant timeCreated) {
        this.createdAt = timeCreated;
    }

    @Nullable
    public Instant getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(@Nullable Instant timeModified) {
        this.modifiedAt = timeModified;
    }

    public Integer getVersion() {
        return version;
    }

    public AbstractEntity setVersion(Integer version) {
        this.version = version;
        return this;
    }
}
