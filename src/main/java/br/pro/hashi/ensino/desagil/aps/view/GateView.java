// A APS está sendo realizada por: Abel Cavalcante de Andrade e Rodrigo Oliveira de Jesus
// Todas as fotos foram retiradas do link: https://en.wikipedia.org/wiki/Logic_gate, sendo que o link para cada fote é:
// AndGate: https://en.wikipedia.org/wiki/File:AND_ANSI.svg
// NandGate: https://en.wikipedia.org/wiki/File:NAND_ANSI.svg
// NotGate: https://en.wikipedia.org/wiki/File:NOT_ANSI.svg
// OrGate: https://en.wikipedia.org/wiki/File:OR_ANSI.svg
// XorGate: https://en.wikipedia.org/wiki/File:XOR_ANSI.svg


package br.pro.hashi.ensino.desagil.aps.view;

import br.pro.hashi.ensino.desagil.aps.model.Gate;
import br.pro.hashi.ensino.desagil.aps.model.Light;
import br.pro.hashi.ensino.desagil.aps.model.Switch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

public class GateView extends FixedPanel implements ItemListener, MouseListener {
    private final Switch[] switches;
    private final Gate gate;
    private final Light light;

    private final JCheckBox[] inputBoxes;

    private final Image image;
    private Color color;

    public GateView(Gate gate) {
        super(230, 110);
        this.gate = gate;
        int inputSize = gate.getInputSize();

        light = new Light();
        switches = new Switch[inputSize];
        inputBoxes = new JCheckBox[inputSize];

        light.setR(255);
        light.setG(0);
        light.setB(0);
        light.connect(0, gate);

        for (int i = 0; i < inputSize; i++) {
            switches[i] = new Switch();
            inputBoxes[i] = new JCheckBox();

            gate.connect(i, switches[i]);
        }

        int multiplicador = 0;
        for (JCheckBox inputBox : inputBoxes) {
            if (inputSize == 2) {
                add(inputBox, 10, 22 + multiplicador * 40, 20, 15);
                multiplicador++;
            } else {
                add(inputBox, 10, 42, 20, 15);
            }
        }

        String name = gate.toString() + ".png";
        URL url = getClass().getClassLoader().getResource(name);
        image = getToolkit().getImage(url);

        addMouseListener(this);

        for (JCheckBox inputBox : inputBoxes) {
            inputBox.addItemListener(this);
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

        int r = light.getR();
        int g = light.getG();
        int b = light.getB();
        color = new Color(r, g, b);
        repaint();
    }

    @Override
    public void itemStateChanged(ItemEvent event) {
        update();
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        int x = event.getX();
        int y = event.getY();

        if (Math.pow(x - 200, 2) + Math.pow(y - 50, 2) < 100) {

            color = JColorChooser.showDialog(this, null, color);
            if (color != null) {
                light.setR(color.getRed());
                light.setG(color.getGreen());
                light.setB(color.getBlue());
            }
            // ...e chamamos repaint para atualizar a tela.
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent event) {

    }

    @Override
    public void mouseReleased(MouseEvent event) {

    }

    @Override
    public void mouseEntered(MouseEvent event) {

    }

    @Override
    public void mouseExited(MouseEvent event) {

    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        g.drawImage(image, 10, 0, 200, 100, this);

        g.setColor(color);
        g.fillOval(190, 40, 20, 20);

        getToolkit().sync();
    }
}
