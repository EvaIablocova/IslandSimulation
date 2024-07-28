package com.javarush.island.iablocova.controller;

import com.javarush.island.iablocova.view.View;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Controller {
    private final View view;

    public View getView()
    {
        return view;
    }
}
