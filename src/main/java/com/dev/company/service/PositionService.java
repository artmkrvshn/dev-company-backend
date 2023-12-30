package com.dev.company.service;

import com.dev.company.aspect.TrackExecutionTime;
import com.dev.company.entity.Position;
import com.dev.company.exception.ForeignKeyViolationException;
import com.dev.company.exception.position.PositionAlreadyExistsException;
import com.dev.company.exception.position.PositionNotFoundException;
import com.dev.company.repository.PositionRepository;
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
public class PositionService {

    private final PositionRepository repository;

    public PositionService(PositionRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Position create(Position position) {
        if (repository.existsByName(position.getName())) {
            throw new PositionAlreadyExistsException("A position with this name already exists.");
        }
        return repository.saveAndFlush(position);
    }

    public Position readById(UUID id) {
        Optional<Position> position = repository.readById(id);
        return position.orElseThrow(() -> new PositionNotFoundException("A position with this ID doesn't exist."));
    }

    public List<Position> readAll() {
        return repository.findAll();
    }

    public List<Position> readAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Transactional
    public void updateById(UUID positionId, Position position) {
        if (!repository.existsById(positionId)) {
            throw new PositionNotFoundException("A position with this ID already exists.");
        }
        if (repository.existsByName(position.getName())) {
            throw new PositionAlreadyExistsException("A position with this name already exists.");
        }
        position.setId(positionId);
        repository.save(position);
    }

    @Transactional
    public void deleteById(UUID id) {
        if (!repository.existsById(id)) {
            throw new PositionNotFoundException("A position with this ID doesn't exist.");
        }
        if (repository.hasEmployees(id)) {
            throw new ForeignKeyViolationException("The position is still referenced in the \"employee\" table.");
        }
        repository.deleteById(id);
    }
}
