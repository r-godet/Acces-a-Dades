/*import org.example.Controller.Controller;
import org.example.Entities.User;
import org.example.Entities.Owner;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
    public class View extends JFrame {
        private JTextField txtNombre;
        private JTextField txtEdad;
        private JTextArea txtAreaUsuarios;
        private JButton btnAgregar;
        public View() {
// Configurar la interfaz gráfica
            this.setTitle("Gestión de Usuarios");
            this.setSize(300, 300);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
// Componentes de la interfaz
            txtNombre = new JTextField();
            txtEdad = new JTextField();
            txtAreaUsuarios = new JTextArea();
            btnAgregar = new JButton("Agregar Usuario");
// Manejar eventos del botón
            btnAgregar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
// Enviar información al controlador cuando se hace clic en el botón
                    View.agregarUsuario(txtNombre.getText(),
                            Integer.parseInt(txtEdad.getText()));
                }

            });
// Agregar componentes a la interfaz
            this.add(new JLabel("Nombre:"));
            this.add(txtNombre);
            this.add(new JLabel("Edad:"));
            this.add(txtEdad);
            this.add(btnAgregar);
            this.add(new JLabel("Usuarios:"));
            this.add(txtAreaUsuarios);
// Mostrar la interfaz
            this.setVisible(true);
        }
        // Método para actualizar la vista con la lista de usuarios
        public void actualizarVista(List<Usuario> usuarios) {
            txtAreaUsuarios.setText("");
            for (Usuario usuario : usuarios) {
                txtAreaUsuarios.append("Nombre: " + usuario.getNombre() + ", Edad: " +
                        usuario.getEdad() + "\n");
            }








        }
    }
}*/
package org.example.View;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class View {
    private static final int CELDA_TAMANO = 20;
    private static final int MIN_DIMENSION = 15;
    private static final int MAX_DIMENSION = 26;

    private JFrame frame;
    private JButton iniciarButton;
    private JButton detenerButton;
    private JButton limpiarButton;
    private JPanel cuadriculaPanel;
    private JTextField filasTextField;
    private JTextField columnasTextField;

    private boolean[][] cuadricula;
    private Timer timer;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new View().crearYMostrarGUI());
    }

    public void crearYMostrarGUI() {
        frame = new JFrame("Contactos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        solicitarDimensiones();
        preguntarMetodoInicial();

        JPanel botonesPanel = new JPanel();
        iniciarButton = new JButton("Iniciar");
        detenerButton = new JButton("Detener");
        limpiarButton = new JButton("Limpiar");
        botonesPanel.add(iniciarButton);
        botonesPanel.add(detenerButton);
        limpiarButton.setEnabled(false); // Se habilita después de inicializar la cuadricula
        botonesPanel.add(limpiarButton);

        cuadriculaPanel = new JPanel();
        cuadriculaPanel.setLayout(new GridLayout(Integer.parseInt(filasTextField.getText()), Integer.parseInt(columnasTextField.getText())));
        inicializarCuadricula();

        iniciarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarSimulacion();
            }
        });

        detenerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                detenerSimulacion();
            }
        });

        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCuadricula();
            }
        });

        frame.add(cuadriculaPanel, BorderLayout.CENTER);
        frame.add(botonesPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void
    private void solicitarDimensiones() {
        JOptionPane.showMessageDialog(frame,"------GESTOR DE CONTACTOS------\n"+
                "\n" +
                "\n- Cada célula con 2 vecinas sobrevive." +
                "\n- Cada célula con 3 o más vecinas muere por superpoblación." +
                "\n- Cada célula con 1 o menos vecinas muere por soledad." +
                "\n- Cada célula muerta con 3 células vecinas vivas, renace.");
        JOptionPane.showMessageDialog(frame,"\nAhora haremos el diseño dle tablero");
        boolean dimensionesValidas = false;
        while (!dimensionesValidas) {
            String filasInput = JOptionPane.showInputDialog(frame, "Ingrese el número de filas (entre " + MIN_DIMENSION + " y " + MAX_DIMENSION + "):");
            String columnasInput = JOptionPane.showInputDialog(frame, "Ingrese el número de columnas (entre " + MIN_DIMENSION + " y " + MAX_DIMENSION + "):");

            try {
                int filas = Integer.parseInt(filasInput);
                int columnas = Integer.parseInt(columnasInput);

                if (filas >= MIN_DIMENSION && filas <= MAX_DIMENSION && columnas >= MIN_DIMENSION && columnas <= MAX_DIMENSION) {
                    filasTextField = new JTextField(filasInput);
                    columnasTextField = new JTextField(columnasInput);
                    dimensionesValidas = true;
                } else {
                    JOptionPane.showMessageDialog(frame, "Por favor, ingrese dimensiones válidas.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Por favor, ingrese valores numéricos válidos.");
            }
        }
    }

    private void preguntarMetodoInicial() {
        Object[] opciones = {"Manual", "Aleatorio"};
        int seleccion = JOptionPane.showOptionDialog(frame, "¿Cómo desea inicializar las células?", "Método de Inicialización", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        if (seleccion == JOptionPane.YES_OPTION) {
            inicializarManualmente();
        } else {
            inicializarAleatoriamente();
        }
    }

    private void inicializarCuadricula() {
        cuadricula = new boolean[Integer.parseInt(filasTextField.getText())][Integer.parseInt(columnasTextField.getText())];
        cuadriculaPanel.removeAll();
        cuadriculaPanel.setLayout(new GridLayout(Integer.parseInt(filasTextField.getText()), Integer.parseInt(columnasTextField.getText())));

        for (int i = 0; i < Integer.parseInt(filasTextField.getText()); i++) {
            for (int j = 0; j < Integer.parseInt(columnasTextField.getText()); j++) {
                JPanel panelCelda = new JPanel();
                panelCelda.setBackground(Color.WHITE);
                panelCelda.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                panelCelda.setPreferredSize(new Dimension(CELDA_TAMANO, CELDA_TAMANO));
                cuadriculaPanel.add(panelCelda);

                int finalI = i;
                int finalJ = j;
                panelCelda.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        toggleEstadoCelda(finalI, finalJ, panelCelda);
                    }
                });
            }
        }

        frame.revalidate();
        frame.repaint();
        limpiarButton.setEnabled(true);
    }

    private void toggleEstadoCelda(int fila, int columna, JPanel panelCelda) {
        cuadricula[fila][columna] = !cuadricula[fila][columna];
        Color nuevoColor = cuadricula[fila][columna] ? Color.BLACK : Color.WHITE;
        panelCelda.setBackground(nuevoColor);
    }

    private void limpiarCuadricula() {
        detenerSimulacion();
        inicializarCuadricula();
    }

    private void iniciarSimulacion() {
        iniciarButton.setEnabled(false);
        detenerButton.setEnabled(true);
        limpiarButton.setEnabled(false);

        timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarCuadricula();
            }
        });
        timer.start();
    }

    private void detenerSimulacion() {
        iniciarButton.setEnabled(true);
        detenerButton.setEnabled(false);
        limpiarButton.setEnabled(true);

        if (timer != null) {
            timer.stop();
        }
    }

    private void actualizarCuadricula() {
        boolean[][] nuevaGeneracion = new boolean[Integer.parseInt(filasTextField.getText())][Integer.parseInt(columnasTextField.getText())];

        for (int i = 0; i < Integer.parseInt(filasTextField.getText()); i++) {
            for (int j = 0; j < Integer.parseInt(columnasTextField.getText()); j++) {
                int vecinas = contarVecinas(i, j);
                if (cuadricula[i][j]) {
                    nuevaGeneracion[i][j] = (vecinas == 2 || vecinas == 3);
                } else {
                    nuevaGeneracion[i][j] = (vecinas == 3);
                }
            }
        }

        cuadricula = nuevaGeneracion;
        actualizarInterfaz();
    }

    private int contarVecinas(int fila, int columna) {
        int count = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue; // Saltar la celda actual
                }

                int vecinaFila = fila + i;
                int vecinaColumna = columna + j;

                if (vecinaFila >= 0 && vecinaFila < Integer.parseInt(filasTextField.getText()) && vecinaColumna >= 0 && vecinaColumna < Integer.parseInt(columnasTextField.getText())) {
                    if (cuadricula[vecinaFila][vecinaColumna]) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private void actualizarInterfaz() {
        for (int i = 0; i < Integer.parseInt(filasTextField.getText()); i++) {
            for (int j = 0; j < Integer.parseInt(columnasTextField.getText()); j++) {
                JPanel panelCelda = (JPanel) cuadriculaPanel.getComponent(i * Integer.parseInt(columnasTextField.getText()) + j);
                Color nuevoColor = cuadricula[i][j] ? Color.BLACK : Color.WHITE;
                panelCelda.setBackground(nuevoColor);
            }
        }
    }

    private void inicializarManualmente() {
        JOptionPane.showMessageDialog(frame, "Haz clic en las celdas para cambiar su estado (viva/muerta).\nDespués, presiona Iniciar para comenzar la simulación.");
    }

    private void inicializarAleatoriamente() {
        boolean[][] nuevaGeneracion = new boolean[Integer.parseInt(filasTextField.getText())][Integer.parseInt(columnasTextField.getText())];
        Random rand = new Random();

        int celulasAleatorias = Integer.parseInt(JOptionPane.showInputDialog(frame, "Ingrese el número de células aleatorias:"));

        for (int k = 0; k < celulasAleatorias; k++) {
            int filaAleatoria = rand.nextInt(Integer.parseInt(filasTextField.getText()));
            int columnaAleatoria = rand.nextInt(Integer.parseInt(columnasTextField.getText()));

            nuevaGeneracion[filaAleatoria][columnaAleatoria] = true;

            // Añadir 3 células al lado de la generada aleatoriamente
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    int vecinaFila = filaAleatoria + i;
                    int vecinaColumna = columnaAleatoria + j;

                    if (vecinaFila >= 0 && vecinaFila < Integer.parseInt(filasTextField.getText()) && vecinaColumna >= 0 && vecinaColumna < Integer.parseInt(columnasTextField.getText())) {
                        nuevaGeneracion[vecinaFila][vecinaColumna] = true;
                    }
                }
            }
        }

        cuadricula = nuevaGeneracion;
        actualizarInterfaz();
    }

}