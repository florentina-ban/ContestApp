package repository;

import domain.Inscriere;
import domain.Participant;
import domain.Proba;

import java.util.Collection;

public interface IRepoInscrieri extends Repo<Inscriere> {
    public Collection<Participant> getParticipantiLaProba(int idProba);
    public Collection<Proba> getProbeLaParticipant(int idPart);
}
