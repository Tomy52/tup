package org.tomy52.tp3_conspring.App;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.tomy52.tp3_conspring.View.Menu;

@SpringBootApplication
@ComponentScan(basePackages = "org.tomy52.tp3_conspring")
public class Main implements CommandLineRunner {
    private final Menu menu;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Autowired
    public Main(Menu menu) {
        this.menu = menu;
    }

    @Override
    public void run(String... args) throws Exception {
        menu.mostrarMenu();
    }
}