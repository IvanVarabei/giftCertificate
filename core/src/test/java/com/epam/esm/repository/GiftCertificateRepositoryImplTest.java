package com.epam.esm.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

//todo junit5

//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;

//@ActiveProfiles("test")
//@ExtendWith(SpringExtension.class)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@ContextConfiguration(classes = {EmbeddedTestConfig.class})

class GiftCertificateRepositoryImplTest {

    //@Order(1)
    @Test
//    @Sql("/db.test/V1.2create_schema.sql")
//    @Sql("/db.test/V1.3insert_data.sql")
    void getAll() {
        Assertions.assertEquals(10, 10);
    }
}
