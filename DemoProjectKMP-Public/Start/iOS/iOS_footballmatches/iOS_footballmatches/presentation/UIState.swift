//
//  UIState.swift
//  iOS_footballmatches
//
//  Created by Menes  SIMEU on 06/06/2024.
//

import Foundation

enum UIState<T> {
    case loading
    case success(T)
    case failure(String)
}
