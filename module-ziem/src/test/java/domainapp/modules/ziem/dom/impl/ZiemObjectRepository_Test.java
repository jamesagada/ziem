/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package domainapp.modules.ziem.dom.impl;

import java.util.List;

import com.google.common.collect.Lists;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.jmock.Expectations;
import org.jmock.Sequence;
import org.jmock.auto.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2;
import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2.Mode;

import static org.assertj.core.api.Assertions.assertThat;

public class ZiemObjectRepository_Test {

    @Rule
    public JUnitRuleMockery2 context = JUnitRuleMockery2.createFor(Mode.INTERFACES_AND_CLASSES);

    @Mock
    ServiceRegistry2 mockServiceRegistry;
    
    @Mock
    RepositoryService mockRepositoryService;

    ZiemObjectRepository ZiemObjectRepository;

    @Before
    public void setUp() throws Exception {
        ZiemObjectRepository = new ZiemObjectRepository();
        ZiemObjectRepository.repositoryService = mockRepositoryService;
        ZiemObjectRepository.serviceRegistry = mockServiceRegistry;
    }

    public static class Create extends ZiemObjectRepository_Test {

        @Test
        public void happyCase() throws Exception {

            final String someName = "Foobar";

            // given
            final Sequence seq = context.sequence("create");
            context.checking(new Expectations() {
                {
                    oneOf(mockServiceRegistry).injectServicesInto(with(any(ZiemObject.class)));
                    inSequence(seq);

                    oneOf(mockRepositoryService).persist(with(nameOf(someName)));
                    inSequence(seq);
                }

            });

            // when
            final ZiemObject obj = ZiemObjectRepository.create(someName);

            // then
            assertThat(obj).isNotNull();
            assertThat(obj.getName()).isEqualTo(someName);
        }

        private static Matcher<ZiemObject> nameOf(final String name) {
            return new TypeSafeMatcher<ZiemObject>() {
                @Override
                protected boolean matchesSafely(final ZiemObject item) {
                    return name.equals(item.getName());
                }

                @Override
                public void describeTo(final Description description) {
                    description.appendText("has name of '" + name + "'");
                }
            };
        }
    }

    public static class ListAll extends ZiemObjectRepository_Test {

        @Test
        public void happyCase() throws Exception {

            // given
            final List<ZiemObject> all = Lists.newArrayList();

            context.checking(new Expectations() {
                {
                    oneOf(mockRepositoryService).allInstances(ZiemObject.class);
                    will(returnValue(all));
                }
            });

            // when
            final List<ZiemObject> list = ZiemObjectRepository.listAll();

            // then
            assertThat(list).isEqualTo(all);
        }
    }
}
