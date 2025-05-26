resource "aws_api_gateway_vpc_link" "ecs_vpc_link" {
  name           = "vpc-link-valida-password"
  target_arns    = [var.nlb_arn]
}

resource "aws_api_gateway_rest_api" "valida_password_gateway" {
  name        = var.api_name
  description = var.api_description
}

resource "aws_api_gateway_resource" "resource_api" {
  rest_api_id = aws_api_gateway_rest_api.valida_password_gateway.id
  parent_id   = aws_api_gateway_rest_api.valida_password_gateway.root_resource_id
  path_part   = "api"
}

resource "aws_api_gateway_resource" "resource_v1" {
  rest_api_id = aws_api_gateway_rest_api.valida_password_gateway.id
  parent_id   = aws_api_gateway_resource.resource_api.id
  path_part   = "v1"
}

resource "aws_api_gateway_resource" "resource_password" {
  rest_api_id = aws_api_gateway_rest_api.valida_password_gateway.id
  parent_id   = aws_api_gateway_resource.resource_v1.id
  path_part   = "password"
}

resource "aws_api_gateway_resource" "resource_validate" {
  rest_api_id = aws_api_gateway_rest_api.valida_password_gateway.id
  parent_id   = aws_api_gateway_resource.resource_password.id
  path_part   = "validate"
}

resource "aws_api_gateway_method" "post_password_validate" {
  rest_api_id   = aws_api_gateway_rest_api.valida_password_gateway.id
  resource_id   = aws_api_gateway_resource.resource_validate.id
  http_method   = "POST"
  authorization = "NONE"
}

resource "aws_api_gateway_integration" "validate_integration" {
  rest_api_id             = aws_api_gateway_rest_api.valida_password_gateway.id
  resource_id             = aws_api_gateway_resource.resource_validate.id
  http_method             = aws_api_gateway_method.post_password_validate.http_method
  integration_http_method = "POST"
  type                    = "HTTP_PROXY"
  uri                     = "http://${var.integration_uri}/api/v1/password/validate"
  connection_type         = "VPC_LINK"
  connection_id           = aws_api_gateway_vpc_link.ecs_vpc_link.id
  depends_on = [
    aws_api_gateway_vpc_link.ecs_vpc_link
  ]
}

resource "aws_api_gateway_deployment" "validate_deployment" {
  rest_api_id = aws_api_gateway_rest_api.valida_password_gateway.id
  stage_name  = var.stage_name

  depends_on = [
    aws_api_gateway_method.post_password_validate,
    aws_api_gateway_integration.validate_integration
  ]
}