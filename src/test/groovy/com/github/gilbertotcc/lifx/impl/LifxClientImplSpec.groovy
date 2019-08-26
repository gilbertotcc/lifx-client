package com.github.gilbertotcc.lifx.impl

import spock.lang.Specification
import spock.lang.Unroll

class LifxClientImplSpec extends Specification {

  @Unroll
  def "validation should return exception with error message"() {
    expect:
    LifxClientImpl.validate(baseUrl, accessToken).getError().getMessage() == errorMessage

    where:
    baseUrl                      | accessToken | errorMessage
    null                         | null        | "baseUrl is null, accessToken is null"
    null                         | "XYZ"       | "baseUrl is null"
    "http://baseurl.example.com" | null        | "accessToken is null"
  }

  def "validation should success if input parameters are not null"() {
    when:
    def validation = LifxClientImpl.validate("http://baseurl.example.com", "XYZ")

    then:
    validation.isValid()
  }
}
