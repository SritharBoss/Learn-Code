package org.example;

import static org.eclipse.swt.events.SelectionListener.*;

import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Main {

    public static void main (String [] args) {
        Display display = new Display ();
        Shell shell = new Shell (display);
        shell.setText("Snippet 60");
        shell.setSize (200, 200);
        shell.open ();
        display.timerExec (5000, () -> System.out.println ("5000"));
        display.timerExec (2000, () -> System.out.println ("2000"));
        while (!shell.isDisposed ()) {
            if (!display.readAndDispatch ()) display.sleep ();
        }
        display.dispose ();
    }
}