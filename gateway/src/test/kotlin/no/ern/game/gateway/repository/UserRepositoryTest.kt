package no.ern.game.gateway.repository

import no.ern.game.gateway.domain.model.UserEntity
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private lateinit var repo: UserRepository

    @Before
    fun setup() {
        Assert.assertEquals(0, repo.count())
    }

    @Test
    fun createUser() {
        val user = UserEntity("name","password")

        repo.save(user)

        assertEquals(1, repo.count())
    }

    @Test
    fun createUser_Custom() {
        // Act
        val user0 = UserEntity("name","password")
        repo.save(user0)

        // Assert
        assertEquals(1, repo.count())
    }

    @Test
    fun findUserByUserName(){
        // Arrange
        val user0 = UserEntity("name","password")
        repo.save(user0)

        // Act
        val user = repo.findUserByUsername("name")
        // note: do not throw exception
        val user1 = repo.findUserByUsername("notExist")

        // Assert
        assertNull(user1)
        assertEquals("password", user!!.password)
    }
}