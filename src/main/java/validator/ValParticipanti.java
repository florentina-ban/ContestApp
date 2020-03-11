package validator;

import domain.Participant;
import myException.ParticipException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.RepoParticipanti;

public class ValParticipanti implements Validator<Participant> {
    private static final Logger logger= LogManager.getLogger();
    private RepoParticipanti repoParticipanti;

    public ValParticipanti() {
    }

    public void setRepoParticipanti(RepoParticipanti repoParticipanti) {
        this.repoParticipanti = repoParticipanti;
    }

    @Override
    public void valideaza(Participant particip) throws ParticipException {
        logger.traceEntry("valideaza participant {}",particip);
        Participant part=repoParticipanti.cautaNume(particip.getNume());
        if (part!=null)
            throw new ParticipException("mai exista un participant cu numele: "+part.getNume());
    }
}
