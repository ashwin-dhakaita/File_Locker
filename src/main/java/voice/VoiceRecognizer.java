package voice;

import com.bitsinharmony.recognito.*;
import utils.Store;
import utils.User;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class VoiceRecognizer {

    static Map<String, VoicePrint> map = null;
    static Store store;

    public VoiceRecognizer(String storeName){
        store = new Store<VoicePrint>(storeName);
        map = store.getStore();
    }

    VoicePrint getVoicePrint(String userKey, String fileName) throws IOException, UnsupportedAudioFileException {

        Recognito<String> recognito = new Recognito<>(16000.0f);
        VoicePrint print = recognito.createVoicePrint(userKey, new File(fileName));
        return print;
    }

    public int recognize(String userKey, String testVoice) throws IOException, UnsupportedAudioFileException {

        Recognito<String> recognito = new Recognito<>(16000.0f, map);

        //recognito.createVoicePrint(userKey, new File(testVoice));

        List<MatchResult<String>> matches = recognito.identify(new File(testVoice));
        MatchResult<String> match = matches.get(0);
        for (ListIterator<MatchResult<String>> it = matches.listIterator(); it.hasNext(); ) {
            MatchResult<String> mach = it.next();
            System.out.println(mach.getKey());
            System.out.println(mach.getLikelihoodRatio());
        }

        if (match.getKey().equals(userKey)) {
            System.out.println(userKey + " is back !!! " + match.getLikelihoodRatio() + "% positive about it...");
            return 1;
        } else {
            System.out.println("Failed");
            return -1;
        }
    }

    public void mergeVoicePrint(String userName, String fileName) throws Exception {
        VoicePrint print = getVoicePrint(userName, fileName);
        store.updateStore(userName, print, store.ADD);
    }

    public void removeVoicePrint(String userName) throws Exception {
        store.updateStore(userName, null, store.REMOVE);
    }

}
