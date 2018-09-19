const electron = require("electron");

electron.app.on("ready", () => {
    const bw = new electron.BrowserWindow({
        width: 400,
        height: 300,
        center: true,
    });
    
    bw.loadURL(`file://${__dirname}/index.html`);    
});