import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { DatasetDataCategoryService } from 'app/entities/dataset-data-category/dataset-data-category.service';
import { IDatasetDataCategory, DatasetDataCategory } from 'app/shared/model/dataset-data-category.model';

describe('Service Tests', () => {
  describe('DatasetDataCategory Service', () => {
    let injector: TestBed;
    let service: DatasetDataCategoryService;
    let httpMock: HttpTestingController;
    let elemDefault: IDatasetDataCategory;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(DatasetDataCategoryService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new DatasetDataCategory(0, 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a DatasetDataCategory', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault,
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new DatasetDataCategory(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a DatasetDataCategory', () => {
        const returnedFromService = Object.assign(
          {
            datasetDataCategoryId: 1,
            dataCategoryRef: 'BBBBBB',
          },
          elemDefault,
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of DatasetDataCategory', () => {
        const returnedFromService = Object.assign(
          {
            datasetDataCategoryId: 1,
            dataCategoryRef: 'BBBBBB',
          },
          elemDefault,
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body),
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a DatasetDataCategory', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
