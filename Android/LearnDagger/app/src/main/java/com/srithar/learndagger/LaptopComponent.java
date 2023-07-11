package com.srithar.learndagger;


import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component
public interface LaptopComponent {

    HardDrive getHardDrive();
    void inject(MainActivity mainActivity);
}
