package br.com.pablopes.stage.app.adapter.repository;

import br.com.pablopes.stage.app.port.repository.JpaTeamModelRepository;
import br.com.pablopes.stage.domain.model.Team;
import br.com.pablopes.stage.domain.repository.TeamRepository;

public class TeamModelRepositoryImpl implements TeamRepository {
    private final JpaTeamModelRepository jpaTeamModelRepository;

    public TeamModelRepositoryImpl(JpaTeamModelRepository jpaTeamModelRepository) {
        this.jpaTeamModelRepository = jpaTeamModelRepository;
    }

    @Override
    public Team save(Team team) {
        return null;
    }
}
