/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package domainapp.modules.ziem.fixture.scenario;

import domainapp.modules.ziem.dom.impl.ZiemObject;
import domainapp.modules.ziem.dom.impl.ZiemObjectMenu;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ZiemObjectData {

    FOO("Foo"),
    BAR("Bar"),
    BAZ("Baz"),
    FRODO("Frodo"),
    FROYO("Froyo"),
    FIZZ("Fizz"),
    BIP("Bip"),
    BOP("Bop"),
    BANG("Bang"),
    BOO("Boo");

    private final String name;

    public ZiemObject createWith(final ZiemObjectMenu menu) {
        return menu.create(name);
    }

    public ZiemObject findWith(final ZiemObjectMenu menu) {
        return menu.findByName(name).get(0);
    }
}
