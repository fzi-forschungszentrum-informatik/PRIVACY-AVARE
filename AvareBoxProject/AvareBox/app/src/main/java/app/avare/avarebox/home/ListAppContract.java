package app.avare.avarebox.home;

import java.util.List;

import app.avare.avarebox.abs.BasePresenter;
import app.avare.avarebox.abs.BaseView;
import app.avare.avarebox.home.models.AppInfo;

/**
 * @author Lody
 * @version 1.0
 */
/*package*/ class ListAppContract {
    interface ListAppView extends BaseView<ListAppPresenter> {

        void startLoading();

        void loadFinish(List<AppInfo> infoList);
    }

    interface ListAppPresenter extends BasePresenter {

    }
}
