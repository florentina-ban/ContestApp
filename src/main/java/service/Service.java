package service;

import domain.Inscriere;
import domain.Participant;
import domain.Proba;
import repository.Repo;

public class Service {
    //public Repo<Participant> repoParticipanti;
    public Repo<Inscriere> repoInscrieri;
   // public Repo<Proba> repoProbe;

    public Service(Repo repoIns) {
      //  this.repoParticipanti = repoPart;
        this.repoInscrieri=repoIns;
       // this.repoProbe=repoProbe;
    }


}
