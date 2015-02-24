create table raffle (
  raffleId integer identity primary key,
  cause varchar(255),
  startdate date,
  enddate date
);

create table player (
  playerId integer identity primary key,
  raffleId integer,
  firstname varchar(255),
  lastname varchar(255),
  gender varchar(1),
  email varchar(255),
  tickets integer
);

create table winner (
  raffleId integer,
  playerId integer,
  payout integer
);