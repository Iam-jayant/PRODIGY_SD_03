// //normal version
// import javax.swing.*;
// import javax.swing.table.DefaultTableModel;
// import java.awt.*;
// import java.awt.event.*;
// import java.io.*;
// import java.util.ArrayList;
// import java.util.List;

// class Contact implements Serializable {
//     private String name;
//     private String phone;
//     private String email;
    
//     public Contact(String name, String phone, String email) {
//         this.name = name;
//         this.phone = phone;
//         this.email = email;
//     }
    
//     public String getName() { return name; }
//     public String getPhone() { return phone; }
//     public String getEmail() { return email; }
    
//     public void setName(String name) { this.name = name; }
//     public void setPhone(String phone) { this.phone = phone; }
//     public void setEmail(String email) { this.email = email; }
// }

// public class ContactManagementSystem extends JFrame {
//     private List<Contact> contacts;
//     private DefaultTableModel tableModel;
//     private JTable contactTable;
//     private JTextField nameField, phoneField, emailField, searchField;
//     private JButton addButton, updateButton, deleteButton, clearButton;
//     private int selectedRow = -1;
//     private static final String DATA_FILE = "contacts.dat";
    
//     public ContactManagementSystem() {
//         super("Contact Management System");
//         contacts = new ArrayList<>();
        
//         // Set up the JFrame
//         setSize(800, 600);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         setLayout(new BorderLayout());
        
//         // Create header panel with gradient background
//         JPanel headerPanel = new JPanel() {
//             @Override
//             protected void paintComponent(Graphics g) {
//                 super.paintComponent(g);
//                 Graphics2D g2d = (Graphics2D) g;
//                 g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//                 int w = getWidth();
//                 int h = getHeight();
//                 Color color1 = new Color(75, 0, 130); // Dark purple
//                 Color color2 = new Color(0, 150, 255); // Blue
//                 GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
//                 g2d.setPaint(gp);
//                 g2d.fillRect(0, 0, w, h);
//             }
//         };
//         headerPanel.setPreferredSize(new Dimension(800, 70));
//         headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        
//         JLabel titleLabel = new JLabel("Contact Management System");
//         titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
//         titleLabel.setForeground(Color.WHITE);
//         headerPanel.add(titleLabel);
        
//         // Create form panel
//         JPanel formPanel = new JPanel();
//         formPanel.setBorder(BorderFactory.createTitledBorder("Contact Details"));
//         formPanel.setLayout(new GridLayout(4, 2, 10, 10));
        
//         formPanel.add(new JLabel("Name:"));
//         nameField = new JTextField();
//         formPanel.add(nameField);
        
//         formPanel.add(new JLabel("Phone:"));
//         phoneField = new JTextField();
//         formPanel.add(phoneField);
        
//         formPanel.add(new JLabel("Email:"));
//         emailField = new JTextField();
//         formPanel.add(emailField);
        
//         // Create button panel
//         JPanel buttonPanel = new JPanel();
//         buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
//         addButton = new JButton("Add");
//         updateButton = new JButton("Update");
//         deleteButton = new JButton("Delete");
//         clearButton = new JButton("Clear");
        
//         buttonPanel.add(addButton);
//         buttonPanel.add(updateButton);
//         buttonPanel.add(deleteButton);
//         buttonPanel.add(clearButton);
        
//         // Create search panel
//         JPanel searchPanel = new JPanel();
//         searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
//         searchPanel.add(new JLabel("Search:"));
//         searchField = new JTextField(20);
//         searchPanel.add(searchField);
        
//         // Combine form and buttons
//         JPanel inputPanel = new JPanel();
//         inputPanel.setLayout(new BorderLayout());
//         inputPanel.add(formPanel, BorderLayout.CENTER);
//         inputPanel.add(buttonPanel, BorderLayout.SOUTH);
        
//         // Create table panel
//         String[] columns = {"Name", "Phone", "Email"};
//         tableModel = new DefaultTableModel(columns, 0);
//         contactTable = new JTable(tableModel);
//         contactTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//         JScrollPane tableScrollPane = new JScrollPane(contactTable);
        
//         // Add components to main frame
//         add(headerPanel, BorderLayout.NORTH);
//         add(inputPanel, BorderLayout.WEST);
//         add(tableScrollPane, BorderLayout.CENTER);
//         add(searchPanel, BorderLayout.SOUTH);
        
//         // Add event listeners
//         addButton.addActionListener(e -> addContact());
//         updateButton.addActionListener(e -> updateContact());
//         deleteButton.addActionListener(e -> deleteContact());
//         clearButton.addActionListener(e -> clearFields());
//         searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
//             public void changedUpdate(javax.swing.event.DocumentEvent e) { searchContacts(); }
//             public void removeUpdate(javax.swing.event.DocumentEvent e) { searchContacts(); }
//             public void insertUpdate(javax.swing.event.DocumentEvent e) { searchContacts(); }
//         });
        
//         contactTable.addMouseListener(new MouseAdapter() {
//             @Override
//             public void mouseClicked(MouseEvent e) {
//                 selectedRow = contactTable.getSelectedRow();
//                 if (selectedRow >= 0) {
//                     nameField.setText((String) tableModel.getValueAt(selectedRow, 0));
//                     phoneField.setText((String) tableModel.getValueAt(selectedRow, 1));
//                     emailField.setText((String) tableModel.getValueAt(selectedRow, 2));
//                 }
//             }
//         });
        
//         // Window closing event to save contacts
//         addWindowListener(new WindowAdapter() {
//             @Override
//             public void windowClosing(WindowEvent e) {
//                 saveContacts();
//             }
//         });
        
//         // Load existing contacts
//         loadContacts();
//         refreshTable();
        
//         setLocationRelativeTo(null);
//         setVisible(true);
//     }
    
//     private void addContact() {
//         String name = nameField.getText().trim();
//         String phone = phoneField.getText().trim();
//         String email = emailField.getText().trim();
        
//         if (name.isEmpty() || phone.isEmpty()) {
//             JOptionPane.showMessageDialog(this, "Name and Phone are required fields", "Error", JOptionPane.ERROR_MESSAGE);
//             return;
//         }
        
//         Contact contact = new Contact(name, phone, email);
//         contacts.add(contact);
//         clearFields();
//         refreshTable();
//         JOptionPane.showMessageDialog(this, "Contact added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
//     }
    
//     private void updateContact() {
//         if (selectedRow < 0) {
//             JOptionPane.showMessageDialog(this, "Please select a contact to update", "Error", JOptionPane.ERROR_MESSAGE);
//             return;
//         }
        
//         String name = nameField.getText().trim();
//         String phone = phoneField.getText().trim();
//         String email = emailField.getText().trim();
        
//         if (name.isEmpty() || phone.isEmpty()) {
//             JOptionPane.showMessageDialog(this, "Name and Phone are required fields", "Error", JOptionPane.ERROR_MESSAGE);
//             return;
//         }
        
//         Contact contact = contacts.get(selectedRow);
//         contact.setName(name);
//         contact.setPhone(phone);
//         contact.setEmail(email);
        
//         clearFields();
//         refreshTable();
//         selectedRow = -1;
//         JOptionPane.showMessageDialog(this, "Contact updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
//     }
    
//     private void deleteContact() {
//         if (selectedRow < 0) {
//             JOptionPane.showMessageDialog(this, "Please select a contact to delete", "Error", JOptionPane.ERROR_MESSAGE);
//             return;
//         }
        
//         int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this contact?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
//         if (confirm == JOptionPane.YES_OPTION) {
//             contacts.remove(selectedRow);
//             clearFields();
//             refreshTable();
//             selectedRow = -1;
//             JOptionPane.showMessageDialog(this, "Contact deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
//         }
//     }
    
//     private void clearFields() {
//         nameField.setText("");
//         phoneField.setText("");
//         emailField.setText("");
//         selectedRow = -1;
//         contactTable.clearSelection();
//     }
    
//     private void searchContacts() {
//         String searchTerm = searchField.getText().toLowerCase().trim();
//         if (searchTerm.isEmpty()) {
//             refreshTable(); // Show all contacts
//             return;
//         }
        
//         tableModel.setRowCount(0);
        
//         for (Contact contact : contacts) {
//             if (contact.getName().toLowerCase().contains(searchTerm) ||
//                 contact.getPhone().toLowerCase().contains(searchTerm) ||
//                 contact.getEmail().toLowerCase().contains(searchTerm)) {
//                 tableModel.addRow(new Object[]{contact.getName(), contact.getPhone(), contact.getEmail()});
//             }
//         }
//     }
    
//     private void refreshTable() {
//         tableModel.setRowCount(0);
        
//         for (Contact contact : contacts) {
//             tableModel.addRow(new Object[]{contact.getName(), contact.getPhone(), contact.getEmail()});
//         }
//     }
    
//     private void saveContacts() {
//         try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
//             oos.writeObject(contacts);
//             System.out.println("Contacts saved successfully");
//         } catch (IOException e) {
//             System.err.println("Error saving contacts: " + e.getMessage());
//         }
//     }
    
//     @SuppressWarnings("unchecked")
//     private void loadContacts() {
//         File file = new File(DATA_FILE);
//         if (!file.exists()) return;
        
//         try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
//             contacts = (List<Contact>) ois.readObject();
//             System.out.println("Loaded " + contacts.size() + " contacts");
//         } catch (IOException | ClassNotFoundException e) {
//             System.err.println("Error loading contacts: " + e.getMessage());
//             contacts = new ArrayList<>(); // Start with empty list if loading fails
//         }
//     }
    
//     public static void main(String[] args) {
//         try {
//             // Set native look and feel
//             UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
        
//         SwingUtilities.invokeLater(() -> new ContactManagementSystem());
//     }
// }
//-------------------------------------------------------------------------------------
//                                  version 2 (using Ai)
//----------------------------------------=--------------------------------------------
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Contact implements Serializable {
    private String name;
    private String phone;
    private String email;

    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }

    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }
}

public class EnhancedContactManager extends JFrame {
    private List<Contact> contacts;
    private DefaultTableModel tableModel;
    private JTable contactTable;
    private JTextField nameField, phoneField, emailField, searchField;
    private JButton addButton, updateButton, deleteButton, clearButton, exportButton;
    private int selectedRow = -1;
    private static final String DATA_FILE = "contacts.dat";

    public EnhancedContactManager() {
        super("📇 Contact Manager Pro");
        contacts = new ArrayList<>();

        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        JPanel header = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, new Color(58, 123, 213), w, h, new Color(58, 96, 115));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        header.setPreferredSize(new Dimension(100, 60));
        JLabel title = new JLabel("Contact Manager Pro");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        header.setLayout(new FlowLayout(FlowLayout.CENTER));
        header.add(title);
        add(header, BorderLayout.NORTH);

        JPanel leftPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        leftPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        nameField = createTextField("Name");
        phoneField = createTextField("Phone");
        emailField = createTextField("Email");

        leftPanel.add(new JLabel("Name:"));
        leftPanel.add(nameField);
        leftPanel.add(new JLabel("Phone:"));
        leftPanel.add(phoneField);
        leftPanel.add(new JLabel("Email:"));
        leftPanel.add(emailField);

        addButton = createButton("Add");
        updateButton = createButton("Update");
        deleteButton = createButton("Delete");
        clearButton = createButton("Clear");
        exportButton = createButton("Export CSV");

        leftPanel.add(addButton);
        leftPanel.add(updateButton);
        leftPanel.add(deleteButton);
        leftPanel.add(clearButton);
        leftPanel.add(exportButton);

        add(leftPanel, BorderLayout.WEST);

        searchField = new JTextField(30);
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        add(searchPanel, BorderLayout.SOUTH);

        String[] columns = {"Name", "Phone", "Email"};
        tableModel = new DefaultTableModel(columns, 0);
        contactTable = new JTable(tableModel);
        contactTable.setFillsViewportHeight(true);
        contactTable.setRowHeight(28);
        JTableHeader headerRow = contactTable.getTableHeader();
        headerRow.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JScrollPane tableScrollPane = new JScrollPane(contactTable);
        add(tableScrollPane, BorderLayout.CENTER);

        // Events
        addButton.addActionListener(e -> addContact());
        updateButton.addActionListener(e -> updateContact());
        deleteButton.addActionListener(e -> deleteContact());
        clearButton.addActionListener(e -> clearFields());
        exportButton.addActionListener(e -> exportCSV());

        contactTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                selectedRow = contactTable.getSelectedRow();
                if (selectedRow >= 0) {
                    nameField.setText((String) tableModel.getValueAt(selectedRow, 0));
                    phoneField.setText((String) tableModel.getValueAt(selectedRow, 1));
                    emailField.setText((String) tableModel.getValueAt(selectedRow, 2));
                }
            }
        });

        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { searchContacts(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { searchContacts(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { searchContacts(); }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                saveContacts();
            }
        });

        loadContacts();
        refreshTable();
        setVisible(true);
    }

    private JTextField createTextField(String placeholder) {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return field;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        return button;
    }

    private void addContact() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();

        if (name.isEmpty() || phone.isEmpty()) {
            showMessage("Name and Phone are required fields", JOptionPane.ERROR_MESSAGE);
            return;
        }
        contacts.add(new Contact(name, phone, email));
        clearFields();
        refreshTable();
        showMessage("Contact added successfully", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateContact() {
        if (selectedRow < 0) {
            showMessage("Please select a contact to update", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Contact contact = contacts.get(selectedRow);
        contact.setName(nameField.getText().trim());
        contact.setPhone(phoneField.getText().trim());
        contact.setEmail(emailField.getText().trim());

        clearFields();
        refreshTable();
        showMessage("Contact updated successfully", JOptionPane.INFORMATION_MESSAGE);
    }

    private void deleteContact() {
        if (selectedRow < 0) {
            showMessage("Please select a contact to delete", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure?", "Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            contacts.remove(selectedRow);
            clearFields();
            refreshTable();
            showMessage("Contact deleted", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void clearFields() {
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
        selectedRow = -1;
        contactTable.clearSelection();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Contact c : contacts) {
            tableModel.addRow(new Object[]{c.getName(), c.getPhone(), c.getEmail()});
        }
    }

    private void searchContacts() {
        String search = searchField.getText().toLowerCase();
        tableModel.setRowCount(0);
        for (Contact c : contacts) {
            if (c.getName().toLowerCase().contains(search) ||
                c.getPhone().toLowerCase().contains(search) ||
                c.getEmail().toLowerCase().contains(search)) {
                tableModel.addRow(new Object[]{c.getName(), c.getPhone(), c.getEmail()});
            }
        }
    }

    private void exportCSV() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("contacts.csv"))) {
            pw.println("Name,Phone,Email");
            for (Contact c : contacts) {
                pw.println(c.getName() + "," + c.getPhone() + "," + c.getEmail());
            }
            showMessage("Contacts exported to contacts.csv", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            showMessage("Error exporting contacts", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showMessage(String msg, int type) {
        JOptionPane.showMessageDialog(this, msg, "Message", type);
    }

    private void saveContacts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(contacts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadContacts() {
        File file = new File(DATA_FILE);
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            contacts = (List<Contact>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            contacts = new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        SwingUtilities.invokeLater(EnhancedContactManager::new);
    }
}
