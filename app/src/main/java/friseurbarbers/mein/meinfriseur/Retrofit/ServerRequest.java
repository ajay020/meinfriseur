package friseurbarbers.mein.meinfriseur.Retrofit;



import friseurbarbers.mein.meinfriseur.Modules.MeinFriseurModule;
import friseurbarbers.mein.meinfriseur.Modules.MeinFriseurModuleHelper;

/**
 * Created by Khushi on 8/15/2017.
 */

public class ServerRequest {
    private String action;
    //private MeinFriseurModule meinFriseurModule;
    private MeinFriseurModuleHelper meinFriseurModuleHelper;



    public void setAction(String action) {
        this.action = action;
    }

    public void setUser(MeinFriseurModuleHelper meinFriseurModuleHelper) {
        this.meinFriseurModuleHelper = meinFriseurModuleHelper;
    }
}
