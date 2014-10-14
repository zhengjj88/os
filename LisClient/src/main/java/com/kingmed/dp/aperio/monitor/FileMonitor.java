/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.aperio.monitor;

import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zhengjunjie
 */
public class FileMonitor  implements Runnable {
    private Path path = null;
    private AtomicBoolean stop = null;

    public static void main(String[] args) {
        AtomicBoolean stop = new AtomicBoolean(false);
        Path myDir = Paths.get("D:/");
        Thread t = new Thread(new FileMonitor(myDir, stop));
        t.start();
    }

    public FileMonitor(Path path, AtomicBoolean stop) {
        this.path = path;
        this.stop = stop;
    }

    @Override
    public void run() {
        try {
            WatchService watcher = path.getFileSystem().newWatchService();
            path.register(watcher, StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
            WatchKey watckKey = watcher.take();
            while (!stop.get()) {
                List<WatchEvent<?>> events = watckKey.pollEvents();
                for (WatchEvent event : events) {
                    if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                        System.out.println("Created: " + event.context().toString());
                    }
                    if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
                        System.out.println("Delete: " + event.context().toString());
                    }
                    if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                        System.out.println("Modify: " + event.context().toString());
                    }
                }
                Thread.currentThread().sleep(500);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(FileMonitor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotDirectoryException ex) {
            System.err.println("Not a directory. Will exit.");
        } catch (IOException ex) {
            Logger.getLogger(FileMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void stop() {
        stop.compareAndSet(false, true);
    }
}
