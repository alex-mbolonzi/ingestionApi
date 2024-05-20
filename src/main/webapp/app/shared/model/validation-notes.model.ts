import { Moment } from 'moment';

export interface IValidationNotes {
  id?: number;
  notesId?: number;
  notes?: string;
  createdBy?: string;
  createdDate?: Moment;
  modifiedBy?: string;
  modifiedDate?: Moment;
  ingestionRequestIdId?: number;
}

export class ValidationNotes implements IValidationNotes {
  constructor(
    public id?: number,
    public notesId?: number,
    public notes?: string,
    public createdBy?: string,
    public createdDate?: Moment,
    public modifiedBy?: string,
    public modifiedDate?: Moment,
    public ingestionRequestIdId?: number,
  ) {}
}
