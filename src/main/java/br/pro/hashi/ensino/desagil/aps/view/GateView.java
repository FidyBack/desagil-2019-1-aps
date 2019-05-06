// FONTE DAS IMAGENS: https://en.wikipedia.org/wiki/Logic_gate (domínio público)

package br.pro.hashi.ensino.desagil.aps.view;

import br.pro.hashi.ensino.desagil.aps.model.Gate;
import br.pro.hashi.ensino.desagil.aps.model.Switch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URL;

public class GateView extends FixedPanel implements ItemListener {
    private static final int BORDER = 10;
    private static final int SWITCH_SIZE = 18;
    private static final int GATE_WIDTH = 90;
    private static final int GATE_HEIGHT = 60;

    private final Switch[] switches;
    private final Gate gate;
    private final JCheckBox[] inputBoxes;
    private final JCheckBox[] outputBoxes;
    private final Image image;

    public GateView(Gate gate) {
        super(BORDER + SWITCH_SIZE + GATE_WIDTH + SWITCH_SIZE + BORDER, GATE_HEIGHT);

        this.gate = gate;

        int inputSize = gate.getInputSize();

        int outputSize = gate.getOutputSize();

        switches = new Switch[inputSize];
        inputBoxes = new JCheckBox[inputSize];
        outputBoxes = new JCheckBox[outputSize];

        for (int i = 0; i < inputSize; i++) {
            switches[i] = new Switch();
            inputBoxes[i] = new JCheckBox();

            gate.connect(i, switches[i]);
        }

//      Novas Modificações de output
        for (int i = 0; i < outputSize; i++) {
            switches[i] = new Switch();
            outputBoxes[i] = new JCheckBox();

            gate.connect(i, switches[i]);
        }


        int xin, yin, stepin;

        xin = BORDER;
        yin = -(SWITCH_SIZE / 2);
        stepin = (GATE_HEIGHT / (inputSize + 1));
        for (JCheckBox inputBox : inputBoxes) {
            yin += stepin;
            add(inputBox, xin, yin, SWITCH_SIZE, SWITCH_SIZE);
        }

        int xou, you, stepou;

        xou = BORDER + SWITCH_SIZE + GATE_WIDTH;
        you = -(SWITCH_SIZE) / 2;
        stepou = (GATE_HEIGHT / (outputSize + 1));

        for (JCheckBox outputBox : outputBoxes) {
            you += stepou;
            add(outputBox, xou, you, SWITCH_SIZE, SWITCH_SIZE);
        }

        String name = gate.toString() + ".png";
        URL url = getClass().getClassLoader().getResource(name);
        image = getToolkit().getImage(url);

        for (JCheckBox inputBox : inputBoxes) {
            inputBox.addItemListener(this);
        }

//      Novas Modificações
        for (JCheckBox outputBox : outputBoxes) {
            outputBox.setEnabled(false);
        }

        update();
    }

    private void update() {
        for (int i = 0; i < gate.getInputSize(); i++) {
            if (inputBoxes[i].isSelected()) {
                switches[i].turnOn();
            } else {
                switches[i].turnOff();
            }
        }

        for (int i = 0; i < gate.getOutputSize(); i++) {
            if (i == 0) {
                boolean result = gate.read(0);
                outputBoxes[i].setSelected(result);
            }
            if (i == 1) {
                boolean result = gate.read(1);
                outputBoxes[i].setSelected(result);
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent event) {
        update();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(image, BORDER + SWITCH_SIZE, 0, GATE_WIDTH, GATE_HEIGHT, this);

        getToolkit().sync();
    }
}
