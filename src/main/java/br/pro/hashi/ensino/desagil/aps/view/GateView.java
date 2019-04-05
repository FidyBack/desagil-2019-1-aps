package br.pro.hashi.ensino.desagil.aps.view;

import br.pro.hashi.ensino.desagil.aps.model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GateView extends JPanel implements ActionListener {

    private final Gate gate;

    private final JCheckBox firstInput;
    private final JCheckBox secondInput;
    private final JCheckBox output;

    public GateView(Gate gate) {
        this.gate = gate;

        firstInput = new JCheckBox();
        secondInput = new JCheckBox();
        output = new JCheckBox();

        JLabel inputLabel = new JLabel("Entrada");
        JLabel outputLabel = new JLabel("Resultado");
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        if (gate.getInputSize() == 2 ){
            add(inputLabel);
            add(firstInput);
            add(secondInput);
            add(outputLabel);
            add(output);

            firstInput.addActionListener(this);
            secondInput.addActionListener(this);
            output.setEnabled(false);
        } else{
            add(inputLabel);
            add(firstInput);
            add(outputLabel);
            add(output);

            firstInput.addActionListener(this);
            output.setEnabled(false);
        }

        update();
    }

    private void update() {
        boolean first;
        boolean second;

        first = firstInput.isSelected();
        second = secondInput.isSelected();

        if (gate.getInputSize() == 2 ) {
            Switch chave1 = new Switch();
            Switch chave2 = new Switch();

            if (first) {
                chave1.turnOn();
            } else {
                chave1.turnOff();
            }
            if (second) {
                chave2.turnOn();
            } else {

                chave2.turnOff();
            }

            gate.connect(0, chave1);
            gate.connect(1, chave2);
        } else{
            Switch chave1 = new Switch();

            if (first) {
                chave1.turnOn();
            } else {
                chave1.turnOff();
            }

            gate.connect(0, chave1);
        }

        boolean out = gate.read();
        output.setSelected(out);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        update();
    }
}
