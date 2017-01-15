/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

package TextExample;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

/* TextDemo.java requires no other files. */


import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import bell.spellingsuggestion.main.MainSpellingSuggestion;

/**
 * @author Michael Bell
 * This class is a simple example of what the spelling suggestion package can
 * do. This is a modified use of Oracle's TextExample in order to show 
 * functionality. The main package is set up to currently parse valid college
 * basketball teams from an internet source. 
 * 
 * In order to test out the functionality, enter some prefix of the team to 
 * search for, hit enter and the results will be displayed below the input field.
 * For example, entering "ak" will return the school "Akron".
 *
 */
@SuppressWarnings("serial")
public class TextExample extends JPanel implements ActionListener {
    protected JTextField textField; //user input line
    protected JTextArea textArea; //will use the text area to show suggestions

    /**
     * Set up the example window. NOTE: These settings are for a QHD laptop.
     * if the text area is extremely large, the font and window size can be
     * reduced.
     */
    public TextExample() {
        super(new GridBagLayout());

        Font fontType = new Font("SansSerif", Font.BOLD, 35);
        
        
        textField = new JTextField(30);
        textField.addActionListener(this);
        textField.setFont(fontType);

        textArea = new JTextArea(15, 30);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setFont(fontType);

        //Add Components to this panel.
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;

        c.fill = GridBagConstraints.HORIZONTAL;
        add(textField, c);

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);
    }

    public void actionPerformed(ActionEvent evt) {
        String prefix = textField.getText();
        
        //clear results box
        textArea.setText(null);
        
        //the the suggestion app to create suggestions
        MainSpellingSuggestion suggestions = new MainSpellingSuggestion(prefix);
        
        //get results and store in suggestionResults
        List<String> suggestionResults = new ArrayList<String>();
        suggestionResults = suggestions.getSuggestionResults();
        
        //print to textArea
        for (String str : suggestionResults){
        	textArea.append(str + "\n");
        }
        
        textField.selectAll();

        //Make sure the new text is visible, even if there
        //was a selection in the text area.
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("TextDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add contents to the window.
        frame.add(new TextExample());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}