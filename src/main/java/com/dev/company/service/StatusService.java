package com.dev.company.service;

import com.dev.company.aspect.TrackExecutionTime;
import com.dev.company.entity.Status;
import com.dev.company.exception.ForeignKeyViolationException;
import com.dev.company.exception.status.StatusAlreadyExistsException;
import com.dev.company.exception.status.StatusNotFoundException;
import com.dev.company.repository.StatusRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@TrackExecutionTime
@Log4j2
@Service
@Transactional(readOnly = true)
public class StatusService {

    private final StatusRepository repository;

    public StatusService(StatusRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Status create(Status status) {
        if (repository.existsByName(status.getName())) {
            throw new StatusAlreadyExistsException("A status with this name already exists.");
        }
        return repository.saveAndFlush(status);
    }

    public Status readById(UUID id) {
        Optional<Status> status = repository.findById(id);
        return status.orElseThrow(() ->
                new StatusNotFoundException("A status with this ID doesn't exist."));
    }

    public List<Status> readAll() {
        return repository.findAll();
    }

    public List<Status> readAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Transactional
    public void updateById(UUID id, Status status) {
        if (!repository.existsById(id)) {
            throw new StatusNotFoundException("A status with this ID doesn't exist.");
        }
        if (repository.existsByName(status.getName())) {
            throw new StatusAlreadyExistsException("A status with this name already exists.");
        }
        status.setId(id);
        repository.save(status);
    }

    @Transactional
    public void deleteById(UUID id) {
        if (!repository.existsById(id)) {
            throw new StatusNotFoundException("A status with this ID doesn't exist.");
        }
        if (repository.hasProjects(id)) {
            throw new ForeignKeyViolationException("The status is still referenced in the \"project\" table.");
        }
        repository.deleteById(id);
    }
}
