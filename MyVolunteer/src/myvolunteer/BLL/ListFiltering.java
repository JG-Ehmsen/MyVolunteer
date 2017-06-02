package myvolunteer.BLL;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import myvolunteer.BE.Volunteer;

public class ListFiltering
{

    /**
     * Given a list of volunteers, filters them by the given string and returns
     * an observable list containing only the objects that match.
     *
     * @param filter
     * @param allUsers
     * @return
     */
    public ObservableList<Volunteer> filter(String filter, List<Volunteer> allUsers)
    {
        ObservableList<Volunteer> filteredList = FXCollections.observableArrayList();
        if (filter.equals(""))
        {
            filteredList.setAll(allUsers);
            return filteredList;
        } else
        {
            for (Volunteer vol : allUsers)
            {
                if (vol.toString().toLowerCase().contains(filter.toLowerCase()))
                {
                    filteredList.add(vol);
                }
            }
            return filteredList;
        }
    }
}
