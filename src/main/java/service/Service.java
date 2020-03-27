package service;

import domain.*;
import javafx.collections.ObservableList;
import myException.RepoException;
import repository.IRepoInscrieri;
import repository.IRepoParticipanti;
import repository.Repo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class Service {
    public IRepoParticipanti repoParticipanti;
    public IRepoInscrieri repoInscrieri;
    public Repo<Proba> repoProbe;
    public Repo<CategVarsta> repoCateg;

    public Service(IRepoInscrieri repoIns,IRepoParticipanti repoPart, Repo<Proba> repoProbe, Repo<CategVarsta> repoCateg) {
        this.repoInscrieri = repoIns;
        this.repoParticipanti=repoPart;
        this.repoProbe=repoProbe;
        this.repoCateg=repoCateg;
    }
    public ArrayList<CategVarsta> getCategVarsta(){
        Collection<CategVarsta> all = repoCateg.findAll();
        ArrayList<CategVarsta> allArr=new ArrayList<>();
        all.forEach(x->allArr.add(x));
        return allArr;
    }

    public ArrayList<CategVarsta> getCategVarsta(int varsta){
        Collection<CategVarsta> all = repoCateg.findAll();
        ArrayList<CategVarsta> allArr=new ArrayList<>();
        all.forEach(x->allArr.add(x));
        return allArr;
    }
    public ArrayList<Proba> getProbe(CategVarsta cat){
        ArrayList<Proba> all=
                (ArrayList<Proba>) repoProbe.findAll().stream()
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
                (ArrayList<Participant>) repoParticipanti.findAll()
                        .stream()
                        .filter(x -> x.getNume().toUpperCase().contains(s.toUpperCase()))
                        .collect(Collectors.toList());
        all.forEach(x -> {
            x.setNrProbe(repoInscrieri.getProbeLaParticipant(x.getId()).size());
        });
        return all;
    }

    public ObservableList<Participant> getParticipantiSearch(ObservableList<Participant> list, String s) {
        ObservableList<Participant> all =list.filtered(x->x.getNume().toUpperCase().contains(s.toUpperCase()));
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

    public ArrayList<Participant> getAllParticipanti(){
        ArrayList<Participant> all=(ArrayList<Participant>)repoParticipanti.findAll();

        all.forEach(x->{
            x.setNrProbe(repoInscrieri.getProbeLaParticipant(x.getId()).size());
        });
        return all;
    }

    public ArrayList<Proba> getProbe(Participant participant){
        ArrayList<Proba> all=(ArrayList<Proba>)repoInscrieri.getProbeLaParticipant(participant.getId());
        return all;
    }
    public ArrayList<Proba> getProbePtVarsta(int varsta){
        ArrayList<Proba> all=
                (ArrayList<Proba>)repoProbe.findAll().stream()
                .filter(x->x.getCategVarsta().apartine(varsta))
                .collect(Collectors.toList());
        return all;
    }
    public void adaugaParticipant(String nume, int varsta, Proba p1, Proba p2) throws RepoException {
        Participant participant=new Participant(nume,varsta);
        int idPart=repoParticipanti.adauga(participant);
        if (p1!=null)
            repoInscrieri.adauga(new Inscriere(p1.getIdProba(),idPart));
        if (p2!=null && p1!=p2)
            repoInscrieri.adauga(new Inscriere(p2.getIdProba(),idPart));
    }
    public void stergeParticipant(Participant p){
        repoParticipanti.sterge(p.getId());
    }

}
