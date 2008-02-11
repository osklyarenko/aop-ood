/*
 * Copyright (c) 2005 Chris Richardson
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
package net.chrisrichardson.bankingExample.domain.hibernate;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * A handy class for testing the performance of tests
 * @author cer
 *
 */

public class ManyAccountPersistencelTests {

    public static Test suite() {
        TestSuite suite = new TestSuite(
                "Test for net.chrisrichardson.bankingExample.domain.hibernate");
        //$JUnit-BEGIN$
        for (int i = 0 ; i < 1000 ; i++) {
            HibernateAccountPersistenceTests test = new HibernateAccountPersistenceTests();
            test.setName("testSimple");
            suite.addTest(test);
        }
        //$JUnit-END$
        return suite;
    }

}
