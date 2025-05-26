resource "aws_vpc" "vpc_private" {
  cidr_block = var.cidr_block
}

resource "aws_subnet" "public_subnet" {
  count = length(var.public_subnets)

  vpc_id            = aws_vpc.vpc_private.id
  cidr_block        = var.public_subnets[count.index]
  availability_zone = var.availability_zones[count.index]
  map_public_ip_on_launch = true
}

resource "aws_subnet" "private_subnet" {
  count = length(var.private_subnets)

  vpc_id            = aws_vpc.vpc_private.id
  cidr_block        = var.private_subnets[count.index]
  availability_zone = var.availability_zones[count.index]
}

resource "aws_internet_gateway" "igw" {
  vpc_id = aws_vpc.vpc_private.id
}

resource "aws_nat_gateway" "nat_gateway" {
  count = length(var.public_subnets)

  allocation_id = element(var.nat_eip_ids, count.index)
  subnet_id     = aws_subnet.public_subnet[count.index].id
}

resource "aws_route_table" "public_route_table" {
  vpc_id = aws_vpc.vpc_private.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.igw.id
  }
}

resource "aws_route_table" "private_route_table" {
  count = length(var.private_subnets)

  vpc_id = aws_vpc.vpc_private.id

  route {
    cidr_block     = "0.0.0.0/0"
    nat_gateway_id = aws_nat_gateway.nat_gateway[count.index].id
  }
}

resource "aws_route_table_association" "public_subnet_assoc" {
  count = length(var.public_subnets)

  subnet_id      = aws_subnet.public_subnet[count.index].id
  route_table_id = aws_route_table.public_route_table.id
}

resource "aws_route_table_association" "private_subnet_assoc" {
  count = length(var.private_subnets)

  subnet_id      = aws_subnet.private_subnet[count.index].id
  route_table_id = aws_route_table.private_route_table[count.index].id
}
