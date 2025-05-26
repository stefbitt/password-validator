
resource "aws_iam_policy" "execution_policy" {
  name        = var.policy_name
  description = "IAM Policy for Terraform execution in AWS environment"
  policy      = data.aws_iam_policy_document.execution_policy.json
}

data "aws_iam_policy_document" "execution_policy" {
  statement {
    actions = [
      # EC2
      "ec2:Describe*",
      "ec2:Get*",
      "ec2:List*",

      # ELB
      "elasticloadbalancing:*",

      # CloudWatch e Logs
      "cloudwatch:*",
      "logs:*",

      # IAM
      "iam:PassRole",
      "iam:GetRole",
      "iam:ListRoles",
      "iam:CreateServiceLinkedRole",

      # ECS
      "ecs:*",

      # SSM
      "ssm:GetParameters",
      "ssm:GetParameter",
      "ssm:DescribeParameters",

      # API Gateway v1
      "apigateway:GET",
      "apigateway:POST",
      "apigateway:PUT",
      "apigateway:DELETE",
      "apigateway:PATCH",

      # API Gateway v2
      "apigatewayv2:*"
    ]
    resources = ["*"]
  }
}
