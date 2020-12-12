const {app, screen, BrowserWindow} = require('electron')
const path = require('path')
const url = require('url')

const child_process = require('child_process');
let jarProcess;

function createWindow() {

    const size = screen.getPrimaryDisplay().workAreaSize;

    // Create the browser window.
    const mainWindow = new BrowserWindow({
        x: 0,
        y: 0,
        width: size.width,
        height: size.height,
        webPreferences: {
            allowRunningInsecureContent: true,
            webSecurity: false
        }
    })

    mainWindow.loadURL(
        url.format({
            pathname: path.join(__dirname, `/dist/index.html`),
            protocol: "file:",
            slashes: true
        })
    )
}

// This method will be called when Electron has finished
// initialization and is ready to create browser windows.
// Some APIs can only be used after this event occurs.
app.whenReady().then(() => {
    let jarApiFileName = path.join(path.dirname(path.resolve(__dirname)), 'lib', 'championship-manager-app.jar');
    jarProcess = child_process.spawn('java', ['-jar', jarApiFileName], {detached: true});
    createWindow()

    app.on('activate', function () {
        // On macOS it's common to re-create a window in the app when the
        // dock icon is clicked and there are no other windows open.
        if (BrowserWindow.getAllWindows().length === 0) createWindow()
    })
})

// Quit when all windows are closed, except on macOS. There, it's common
// for applications and their menu bar to stay active until the user quits
// explicitly with Cmd + Q.
app.on('window-all-closed', function () {
    if (process.platform !== 'darwin') app.quit()
})

app.on('before-quit', function () {
    jarProcess.kill()
});
// In this file you can include the rest of your app's specific main process
// code. You can also put them in separate files and require them here.
