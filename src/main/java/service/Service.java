package service;

import domain.CategVarsta;
import domain.Inscriere;
import domain.Participant;
import domain.Proba;
import repository.IRepoInscrieri;
import repository.Repo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class Service {
    public Repo<Participant> repoParticipanti;
    public IRepoInscrieri repoInscrieri;
    public Repo<Proba> repoProbe;
    public Repo<CategVarsta> repoCateg;

    public Service(IRepoInscrieri repoIns,Repo<Participant> repoPart, Repo<Proba> repoProbe, Repo<CategVarsta> repoCateg) {
        this.repoInscrieri = repoIns;
        this.repoParticipanti=repoPart;
        this.repoProbe=repoProbe;
        this.repoCateg=repoCateg;
    }
    public ArrayList<CategVarsta> getCategVarsta(){
        Collection<CategVarsta> all = repoCateg.getAll();
        ArrayList<CategVarsta> allArr=new ArrayList<>();
        all.forEach(x->allArr.add(x));
        return allArr;
    }
    public ArrayList<Proba> getProbe(CategVarsta cat){
        ArrayList<Proba> all=
                (ArrayList<Proba>) repoProbe.getAll().stream()
                        .filter(x->x.getCategVarsta().equals(cat))
                        .collect(Collectors.toList());
        return all;
    }

    public ArrayList<Participant> getParticipanti(Proba proba){
        ArrayList<Participant> all=
                (ArrayList<Participant>) repoInscrieri.getParticipantiLaProba(proba.getIdProba());
        all.forEach(x->{
            x.setNrProbe(repoInscrieri.getProbeLaParticipant(x.getId()).size());
        });
        return all;
    }

    public ArrayList<Participant> getParticipantiSearch(String s) {
        ArrayList<Participant> all =
                (ArrayList<Participant>) repoParticipanti.getAll()
                        .stream()
                        .filter(x -> x.getNume().toUpperCase().contains(s.toUpperCase()))
                        .collect(Collectors.toList());
        all.forEach(x -> {
            x.setNrProbe(repoInscrieri.getProbeLaParticipant(x.getId()).size());
        });
        return all;
    }

    public ArrayList<Participant> getParticipantiSearch(String s,Proba proba){
        ArrayList<Participant> all=
                (ArrayList<Participant>) repoInscrieri.getParticipantiLaProba(proba.getIdProba())
                .stream()
                .filter(x->x.getNume().toUpperCase().contains(s.toUpperCase()))
                .collect(Collectors.toList());

        all.forEach(x->{
            x.setNrProbe(repoInscrieri.getProbeLaParticipant(x.getId()).size());
        });
        return all;
    }

    public ArrayList<Proba> getProbe(Participant participant){
        ArrayList<Proba> all=(ArrayList<Proba>)repoInscrieri.getProbeLaParticipant(participant.getId());
        return all;
    }

}
