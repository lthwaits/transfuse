package org.androidtransfuse.integrationTest.menu;

import android.content.Context;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import org.androidtransfuse.annotations.*;
import org.androidtransfuse.integrationTest.R;
import org.androidtransfuse.listeners.ActivityMenuComponent;

import javax.inject.Inject;

import static org.androidtransfuse.integrationTest.SharedVariables.ONE_SECOND;

/**
 * @author John Ericksen
 */
@Activity(label = "Menu")
@Layout(R.layout.display)
@RegisterListener
public class Menu implements ActivityMenuComponent {

    private static final String INSTRUCTIONS = "Click the menu button to display the menu options.";

    private MenuInflater menuInflater;
    private Context context;
    private TextView displayText;

    @Inject
    public Menu(MenuInflater menuInflater, Context context, @View(R.id.displayText) TextView displayText) {
        this.menuInflater = menuInflater;
        this.context = context;
        this.displayText = displayText;
    }
    
    @OnCreate
    public void updateDisplayText(){
        displayText.setText(INSTRUCTIONS);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        menuInflater.inflate(R.menu.example_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.menu_one:
                Toast.makeText(context, "One", ONE_SECOND).show();
                return true;
            case R.id.menu_two:
                Toast.makeText(context, "Two", ONE_SECOND).show();
                return true;
            case R.id.menu_three:
                Toast.makeText(context, "Three", ONE_SECOND).show();
                return true;
            case R.id.menu_four:
                Toast.makeText(context, "Four", ONE_SECOND).show();
                return true;
        }
        return false;
    }

    @Override
    public boolean onMenuOpened(int featureId, android.view.Menu menu) {
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(android.view.Menu menu) {
        return true;
    }

    @Override
    public void onOptionsMenuClosed(android.view.Menu menu) {
        //noop
    }
}