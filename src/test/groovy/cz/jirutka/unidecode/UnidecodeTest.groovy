/*
 * Copyright 2013 徐晨阳 <xuender@gmail.com>.
 * Copyright 2014 Jakub Jirutka <jakub@jirutka.cz>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cz.jirutka.unidecode

import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class UnidecodeTest extends Specification {

    def udAscii = Unidecode.withCharset('ASCII')
    
    def 'convert to ASCII: #input'() {
        expect:
            udAscii.decode(input) == expected
        where:
            input           | expected
            ''              | ''
            null            | ''
            'Hello world.'  | 'Hello world.'
            '南无阿弥陀佛'    | 'Nan Wu A Mi Tuo Fo'
            'Κνωσός'       | 'Knosos'
            'あみだにょらい'  | 'amidaniyorai'
            '一条会走路的鱼'  | 'Yi Tiao Hui Zou Lu De Yu'
    }
    
    def 'convert to initials in ASCII'() {
        expect:
            udAscii.toInitials(input) == expected
        where:
            input                                  | expected
            ''                                     | ''
            null                                   | ''
            'Hello world.'                         | 'Hw'
            '南无阿弥陀佛'                           | 'NWAMTF'
            'Κνωσός'                               | 'K'
            'あみだにょらい'                         | 'a'
            '小小姑娘\n清早起床\n\r提着花篮\t上市场。'  | 'XXGN\nQZQC\n\rTZHL\tSSC'
    }
    
    def 'section out of cacheable area: #input'() {
        expect:
            udAscii.decode(input) == expected
        where:
            input | expected
            '😜'  | ''
            'Ｈ'  | 'H'
    }
}
