package org.tomy52.tp3_conspring.App;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.tomy52.tp3_conspring.View.Menu;

@Component
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