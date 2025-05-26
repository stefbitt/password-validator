Feature: Password validation via REST API

  Scenario Outline: Validate password and check response structure
    Given the password "<password>"
    When the password is sent to the API
    Then the response status should be <status>
    And the field isValid should be <isValid>
    And the errors list size should be <errorsSize>
    Examples:
      | password       | status | isValid | errorsSize |
      | Abcdef1!@      | 200    | true    | 0          |
      | abc abc        | 200    | false   | 6          |
      | abc            | 200    | false   | 4          |
      |                | 400    | false   | 1          |
      | ABC123!!ABC    | 200    | false   | 2          |
