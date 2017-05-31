package myvolunteer.BLL;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import myvolunteer.BE.Volunteer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jeppe
 */
public class ListFilteringTest
{

    public ListFilteringTest()
    {
    }

    /**
     * Test of filter method, of class ListFiltering.
     */
    @Test
    public void testFilter()
    {
        String filter = "Lars";
        Volunteer a = new Volunteer(99999, "Lars", "1");
        Volunteer b = new Volunteer(99999, "Bo", "1");
        Volunteer c = new Volunteer(99999, "Per", "1");

        List<Volunteer> allUsers = new ArrayList();

        allUsers.add(a);
        allUsers.add(b);
        allUsers.add(c);

        ListFiltering instance = new ListFiltering();
        ObservableList<Volunteer> expResult = FXCollections.observableArrayList();
        Volunteer d = new Volunteer(99999, "Lars", "1");
        expResult.add(d);

        ObservableList<Volunteer> result = instance.filter(filter, allUsers);
        for (Volunteer res : result)
        {
            for (Volunteer exp : expResult)
            {
                assertEquals(exp.toString(), res.toString());
            }
        }
        // TODO review the generated test code and remove the default call to fail.
    }

}
