using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

class Program
{
    [STAThread]
    static void Main()
    {
        Application.EnableVisualStyles();
        Application.Run(new Form1());
    }
}

class Form1 : Form
{
    public Form1()
    {
        refreshButtons();
    }

    void refreshButtons()
    {
        Controls.Clear();
        {
            Button refreshButton = new Button()
            {
                Text = "Refresh",
                Location = new Point(this.Width / 2 - 120, 0),
                Size = new Size(240, 30),
            };
            refreshButton.Click += new EventHandler(refresh_Click);
            Controls.Add(refreshButton);
        }
        var buttons = Screen.AllScreens.Select((screen, index) =>
        {
            Button button = new Button()
            {
                Text = screen.DeviceName + "\n" + screen.Bounds.ToString(),
                Location = new Point(this.Width / 2 - 120, 30 + 60 * index),
                Size = new Size(240, 60),
                TabIndex = index,
            };
            button.Click += new EventHandler(button_Click);
            return button;
        });

        foreach (Button button in buttons)
        {
            Controls.Add(button);
        }
    }

    void refresh_Click(object sender, EventArgs e)
    {
        refreshButtons();
    }

    void button_Click(object sender, EventArgs e)
    {
        var screen = Screen.AllScreens[(sender as Button).TabIndex];
        var testWindow = new TestWindow(screen.Bounds);
        testWindow.Show();
    }
}

class TestWindow : Form
{
    public TestWindow(Rectangle bounds) {
        this.StartPosition = FormStartPosition.Manual;
        this.Location = bounds.Location;
        this.Size = bounds.Size;
    }
}
