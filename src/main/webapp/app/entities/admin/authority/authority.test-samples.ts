import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: 'b35fabd7-47fb-4724-9c90-07e1757b7f9d',
};

export const sampleWithPartialData: IAuthority = {
  name: '6b99795e-6bb2-4cea-848c-b0738db5cec3',
};

export const sampleWithFullData: IAuthority = {
  name: '929e9f46-e4ba-4875-97a0-90c30568def8',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
