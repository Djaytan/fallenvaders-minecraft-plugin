/*
 *  This file is part of the FallenVaders distribution (https://github.com/FallenVaders).
 *  Copyright © 2021 Loïc DUBOIS-TERMOZ.
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, version 3.
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package fr.fallenvaders.minecraft.test_server;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A record of the program properties.
 *
 * @author Voltariuss
 * @since 0.2.0
 *
 * @param jarName The jar file name of the server.
 * @param jvmArgs The JVM args passed at server execution.
 * @param guiActive Tells if the GUI should be shown or not at server execution.
 */
public record CommandProperties(@NotNull String jarName, @NotNull List<String> jvmArgs, @NotNull boolean guiActive) {}
