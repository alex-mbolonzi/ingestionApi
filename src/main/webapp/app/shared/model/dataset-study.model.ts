export interface IDatasetStudy {
  id?: number;
  datasetStudyId?: number;
  studyId?: string;
  datasetIdId?: number;
}

export class DatasetStudy implements IDatasetStudy {
  constructor(
    public id?: number,
    public datasetStudyId?: number,
    public studyId?: string,
    public datasetIdId?: number,
  ) {}
}
