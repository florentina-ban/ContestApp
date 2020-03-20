package repository;

import domain.Inscriere;
import domain.Participant;
import domain.Proba;
import validator.ValInscriere;

import java.util.Collection;

public interface IRepoInscrieri extends Repo<Inscriere> {
    RepoParticipanti repoParticipanti=null;
    RepoProbe repoProbe=null;
    ValInscriere valInscriere=null;


    public Collection<Participant> getParticipantiLaProba(int idProba);
    public Collection<Proba> getProbeLaParticipant(int idPart);
}
